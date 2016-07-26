package com.sourcesense.crl.script;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.Pair;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.parser.ParsingReader;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Undefined;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptResponse;

public class ExcelGeneratorScript extends BaseScopableProcessorExtension {

	public static final String MODEL_CSV = "csv";
	public static final String MODEL_EXCEL = "excel";
	private static final Log logger = LogFactory.getLog(ExcelGeneratorScript.class);
	protected DictionaryService dictionaryService;
	private NodeService nodeService;
	private ContentService contentService;
	private String[] headings;
	private NamespaceService namespaceService;
	private PersonService personService;
	private String[] modelProperties;

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

	public void setModelProperties(String modelPropertiesString) {
		this.modelProperties = modelPropertiesString.split(";");
	}

	public void setHeadings(String headingsString) {
		this.headings = headingsString.split(";");
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	private Map<QName, List<QName>> modelOrder;

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public Map<String, Object> generateSpreadsheet(String format, NativeArray resource) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("success", Boolean.TRUE);
		if ("csv".equals(format) || "xls".equals(format) || "xlsx".equals(format) || "excel".equals(format)) {
			// Generate the spreadsheet
			try {
				generateSpreadsheetImpl(getNodeRefList(resource), format, model);
				return model;
			} catch (IOException e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Unable to generate template file", e);
			}
		}
		return model;
	}

	private List<NodeRef> getNodeRefList(NativeArray resource) {
		List<NodeRef> result = new ArrayList<NodeRef>();
		for (int i = 0; i < resource.getLength(); i++) {
			NativeJavaObject object = (NativeJavaObject) resource.get(i, resource);
			String data = null;
			Object dataObject = object.unwrap();

			if (!(dataObject instanceof Undefined)) {
				data = (String) dataObject;
			}
			result.add(new NodeRef(data));
		}
		return result;
	}

	public void generateSpreadsheetImpl(List<NodeRef> resource, String format, Map<String, Object> model)
			throws IOException {
		Pattern qnameMunger = Pattern.compile("([A-Z][a-z]+)([A-Z].*)");

		// Build up the details of the header
		List<Pair<QName, Boolean>> propertyDetails = buildModelPropertiesQName();
		// Build a list of just the properties
		List<QName> properties = new ArrayList<QName>(propertyDetails.size());
		for (Pair<QName, Boolean> p : propertyDetails) {
			QName qn = null;
			if (p != null) {
				qn = p.getFirst();
			}
			properties.add(qn);
		}

		// Output
		if ("csv".equals(format)) {
			StringWriter sw = new StringWriter();
			CSVPrinter csv = new CSVPrinter(sw, CSVStrategy.EXCEL_STRATEGY);
			csv.println(headings);

			populateBody(resource, csv, properties);

			model.put(MODEL_CSV, sw.toString());
		} else {
			Workbook wb;
			if ("xlsx".equals(format)) {
				wb = new XSSFWorkbook();
				// TODO Properties
			} else {
				wb = new HSSFWorkbook();
				// TODO Properties
			}

			// Add our header row
			Sheet sheet = wb.createSheet("Export");
			Row hr = sheet.createRow(0);
			try {
				sheet.createFreezePane(0, 1);
			} catch (IndexOutOfBoundsException e) {
				// https://issues.apache.org/bugzilla/show_bug.cgi?id=51431 &
				// http://stackoverflow.com/questions/6469693/apache-poi-clearing-freeze-split-panes
			}
			Font fb = wb.createFont();
			fb.setBoldweight(Font.BOLDWEIGHT_BOLD);
			Font fi = wb.createFont();
			fi.setBoldweight(Font.BOLDWEIGHT_BOLD);
			fi.setItalic(true);

			CellStyle csReq = wb.createCellStyle();
			csReq.setFont(fb);
			// CellStyle csOpt = wb.createCellStyle();
			// csOpt.setFont(fi);

			// Populate the header
			Drawing draw = null;
			for (int i = 0; i < headings.length; i++) {
				Cell c = hr.createCell(i);
				c.setCellValue(headings[i]);

				// if (required[i]) {
				c.setCellStyle(csReq);
				// } else {
				// c.setCellStyle(csOpt);
				// }

				if (headings[i].length() == 0) {
					sheet.setColumnWidth(i, 3 * 250);
				} else {
					sheet.setColumnWidth(i, 18 * 250);
				}

				// if (descriptions[i] != null && descriptions[i].length() > 0)
				// {
				// // Add a description for it too
				// if (draw == null) {
				// draw = sheet.createDrawingPatriarch();
				// }
				// ClientAnchor ca =
				// wb.getCreationHelper().createClientAnchor();
				// ca.setCol1(c.getColumnIndex());
				// ca.setCol2(c.getColumnIndex() + 1);
				// ca.setRow1(hr.getRowNum());
				// ca.setRow2(hr.getRowNum() + 2);
				//
				// Comment cmt = draw.createCellComment(ca);
				// cmt.setAuthor("");
				// cmt.setString(wb.getCreationHelper().createRichTextString(descriptions[i]));
				// cmt.setVisible(false);
				// c.setCellComment(cmt);
				// }
			}

			// Have the contents populated
			populateBody(resource, wb, sheet, properties);

			// Save it for the template
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wb.write(baos);
			model.put(MODEL_EXCEL, baos.toByteArray());
		}
	}

	protected List<Pair<QName, Boolean>> buildModelPropertiesQName() {

		List<Pair<QName, Boolean>> properties = new ArrayList<Pair<QName, Boolean>>();
		for (String qnameString : modelProperties) {
			String[] qnameStringSplitted = qnameString.split(":");
			String prefix = qnameStringSplitted[0];
			String localName = qnameStringSplitted[1];
			properties.add(new Pair<QName, Boolean>(QName.createQName(prefix, localName, namespaceService), true));
		}
		return properties;
	}

	protected void populateBody(Object resource, CSVPrinter csv, List<QName> properties) throws IOException {
		throw new WebScriptException(Status.STATUS_BAD_REQUEST, "CSV not currently supported");
	}

	protected void populateBody(List<NodeRef> resource, Workbook workbook, Sheet sheet, List<QName> properties)
			throws IOException {

		// Our various formats
		DataFormat formatter = workbook.createDataFormat();
		CreationHelper createHelper = workbook.getCreationHelper();

		CellStyle styleInt = workbook.createCellStyle();
		styleInt.setDataFormat(formatter.getFormat("0"));
		CellStyle styleDate = workbook.createCellStyle();
		styleDate.setDataFormat(formatter.getFormat("yyyy-mm-dd"));
		CellStyle styleDouble = workbook.createCellStyle();
		styleDouble.setDataFormat(formatter.getFormat("General"));
		CellStyle styleNewLines = workbook.createCellStyle();
		styleNewLines.setWrapText(true);

		CellStyle hlink_style = workbook.createCellStyle();
		Font hlink_font = workbook.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		// Export the items
		int rowNum = 1, colNum = 0;
		for (NodeRef item : resource) {
			Row r = sheet.createRow(rowNum);

			colNum = 0;
			for (QName prop : properties) {
				Cell c = r.createCell(colNum);

				Serializable val = nodeService.getProperty(item, prop);
				if (val == null) {
					// Is it an association, or just missing?
					List<AssociationRef> assocs = nodeService.getTargetAssocs(item, prop);
					Set<QName> qnames = new HashSet<QName>(1, 1.0f);
					qnames.add(prop);
					List<ChildAssociationRef> childAssocs = nodeService.getChildAssocs(item, qnames);
					if (assocs.size() > 0) {
						StringBuffer text = new StringBuffer();
						int lines = 1;

						for (AssociationRef ref : assocs) {
							NodeRef child = ref.getTargetRef();
							QName type = nodeService.getType(child);
							if (ContentModel.TYPE_PERSON.equals(type)) {
								if (text.length() > 0) {
									text.append('\n');
									lines++;
								}
								text.append(nodeService.getProperty(child, ContentModel.PROP_FIRSTNAME));
								text.append(" ");
								text.append(nodeService.getProperty(child, ContentModel.PROP_LASTNAME));
							} else if (ContentModel.TYPE_CONTENT.equals(type)) {
								// TODO Link to the content
								if (text.length() > 0) {
									text.append('\n');
									lines++;
								}
								text.append(nodeService.getProperty(child, ContentModel.PROP_NAME));
								text.append(" (");
								text.append(nodeService.getProperty(child, ContentModel.PROP_TITLE));
								text.append(") ");
								/*
								 * MessageFormat.format(
								 * CONTENT_DOWNLOAD_PROP_URL, new Object[] {
								 * child.getStoreRef().getProtocol(),
								 * child.getStoreRef().getIdentifier(),
								 * child.getId(),
								 * URLEncoder.encode((String)nodeService.
								 * getProperty(child, ContentModel.PROP_TITLE)),
								 * URLEncoder.encode(ContentModel.PROP_CONTENT.
								 * toString()) });
								 */
								/*
								 * currently only one link per cell possible
								 * Hyperlink link =
								 * createHelper.createHyperlink(Hyperlink.
								 * LINK_URL);
								 * link.setAddress("http://poi.apache.org/");
								 * c.setHyperlink(link);
								 * c.setCellStyle(hlink_style);
								 */
							} else if (ApplicationModel.TYPE_FILELINK.equals(type)) {
								NodeRef linkRef = (NodeRef) nodeService.getProperty(child,
										ContentModel.PROP_LINK_DESTINATION);
								if (linkRef != null) {
									if (text.length() > 0) {
										text.append('\n');
										lines++;
									}
									text.append("link to: ");
									try {
										text.append(nodeService.getProperty(linkRef, ContentModel.PROP_NAME));
										text.append(" (");
										text.append(nodeService.getProperty(linkRef, ContentModel.PROP_TITLE));
										text.append(") ");
									} catch (Exception e) {
										text.append(nodeService.getProperty(child, ContentModel.PROP_NAME));
										text.append(" (");
										text.append(nodeService.getProperty(child, ContentModel.PROP_TITLE));
										text.append(") ");

									}
								}
							} else {
								System.err.println("TODO: handle " + type + " for " + child);
							}
						}

						String v = text.toString();
						c.setCellValue(v);
						if (lines > 1) {
							c.setCellStyle(styleNewLines);
							r.setHeightInPoints(lines * sheet.getDefaultRowHeightInPoints());
						}
					} else if (childAssocs.size() > 0) {
						StringBuffer text = new StringBuffer();
						for (ChildAssociationRef childAssociationRef : childAssocs) {
							NodeRef child = childAssociationRef.getChildRef();
							QName type = nodeService.getType(child);
							if (type.equals(ForumModel.TYPE_FORUM)) {
								List<ChildAssociationRef> topics = nodeService.getChildAssocs(child);
								if (topics.size() > 0) {
									ChildAssociationRef topicRef = topics.get(0);
									List<ChildAssociationRef> comments = nodeService
											.getChildAssocs(topicRef.getChildRef());
									for (ChildAssociationRef commentChildRef : comments) {
										NodeRef commentRef = commentChildRef.getChildRef();

										ContentData data = (ContentData) nodeService.getProperty(commentRef,
												ContentModel.PROP_CONTENT);
										TemplateContentData contentData = new TemplateContentData(data,
												ContentModel.PROP_CONTENT);

										String commentString = "";
										try {
											commentString = contentData.getContentAsText(commentRef, -1);
										} catch (Exception e) {
											logger.warn("failed to extract content for nodeRef " + commentRef, e);
										}

										String creator = (String) nodeService.getProperty(commentRef,
												ContentModel.PROP_CREATOR);
										NodeRef person = personService.getPerson(creator, false);
										if (person != null) {
											creator = nodeService.getProperty(person, ContentModel.PROP_FIRSTNAME) + " "
													+ nodeService.getProperty(person, ContentModel.PROP_LASTNAME);
										}
										Date created = (Date) nodeService.getProperty(commentRef,
												ContentModel.PROP_CREATED);

										text.append(creator).append(" (")
												.append(DateFormatUtils.format(created, "yyyy-MM-dd")).append("):\n ");
										text.append(commentString).append("\n");
									}
								}
							}
						}
						String v = text.toString();
						c.setCellValue(v);
						c.setCellStyle(styleNewLines);

					} else {
						// This property isn't set
						c.setCellType(Cell.CELL_TYPE_BLANK);
					}
				} else {
					// Regular property, set
					if (val instanceof String) {
						c.setCellValue((String) val);
						c.setCellStyle(styleNewLines);
					} else if (val instanceof Date) {
						c.setCellValue((Date) val);
						c.setCellStyle(styleDate);
					} else if (val instanceof Integer || val instanceof Long) {
						double v = 0.0;
						if (val instanceof Long)
							v = (double) (Long) val;
						if (val instanceof Integer)
							v = (double) (Integer) val;
						c.setCellValue(v);
						c.setCellStyle(styleInt);
					} else if (val instanceof Float || val instanceof Double) {
						double v = 0.0;
						if (val instanceof Float)
							v = (double) (Float) val;
						if (val instanceof Double)
							v = (double) (Double) val;
						c.setCellValue(v);
						c.setCellStyle(styleDouble);
					} else {
						// TODO
						System.err.println("TODO: handle " + val.getClass().getName() + " - " + val);
					}
				}

				colNum++;
			}

			rowNum++;
		}

		// Sensible column widths please!
		colNum = 0;
		for (QName prop : properties) {
			try {
				sheet.autoSizeColumn(colNum);
			} catch (IllegalArgumentException e) {
				sheet.setColumnWidth(colNum, 40 * 256);
			}

			colNum++;
		}
	}

	public static class WriteExcel {
		private String format;
		private String filenameBase;
		private WebScriptResponse res;
		private Map<String, Object> model;

		private WriteExcel(WebScriptResponse res, Map<String, Object> model, String format, String filenameBase) {
			this.res = res;
			this.model = model;
			this.format = format;
			this.filenameBase = filenameBase;
		}

		public void write() throws IOException {
			String filename = filenameBase + "." + format;

			// If it isn't a CSV, reset so we can send binary
			if (!"csv".equals(format)) {
				res.reset();
			}

			// Tell the browser it's a file download
			res.addHeader("Content-Disposition", "attachment; filename=" + filename);

			// Now send that data
			if ("csv".equals(format)) {
				res.getWriter().append((String) model.get(MODEL_CSV));
			} else {
				// Set the mimetype, as we've reset
				if ("xlsx".equals(format)) {
					res.setContentType(MimetypeMap.MIMETYPE_OPENXML_SPREADSHEET);
				} else {
					res.setContentType(MimetypeMap.MIMETYPE_EXCEL);
				}

				// Send the raw excel bytes
				byte[] excel = (byte[]) model.get(MODEL_EXCEL);
				res.getOutputStream().write(excel);
			}
		}
	}

	/**
	 * Inner class wrapping and providing access to a ContentData property.
	 * Slightly modified copy of
	 * {@link org.alfresco.repo.template.BaseContentNode.TemplateContentData}
	 */
	private class TemplateContentData {

		private ContentData contentData;
		private QName property;

		/**
		 * Constructor
		 * 
		 * @param contentData
		 *            The ContentData object this object wraps
		 * @param property
		 *            The property the ContentData is attached too
		 */
		public TemplateContentData(ContentData contentData, QName property) {
			this.contentData = contentData;
			this.property = property;
		}

		/**
		 * @param nodeRef
		 * @param length
		 * @return the content stream to the specified maximum length in
		 *         characters
		 */
		public String getContentMaxLength(final NodeRef nodeRef, int length) {
			ContentReader reader = contentService.getReader(nodeRef, property);

			return (reader != null && reader.exists()) ? reader.getContentString(length) : "";
		}

		/**
		 * @param nodeRef
		 * @param length
		 *            Length of the character stream to return, or -1 for all
		 * @return the binary content stream converted to text using any
		 *         available transformer if fails to convert then null will be
		 *         returned
		 */
		public String getContentAsText(final NodeRef nodeRef, int length) {
			String result = null;

			String mimetype = contentData.getMimetype();
			if (MimetypeMap.MIMETYPE_TEXT_PLAIN.equals(mimetype)) {
				result = getContentMaxLength(nodeRef, length);
			} else {
				// try to use Apache Tika Framework
				if (!MimetypeMap.MIMETYPE_HTML.equals(mimetype)) {
					result = getContentTextViaTika(nodeRef, length);
				}
				if (result == null || "".equalsIgnoreCase(result.trim())) {
					result = getContentTextViaAlfresco(nodeRef, length);
				}
			}

			return result;
		}

		private String getContentTextViaAlfresco(final NodeRef nodeRef, int length) {
			String result = null;
			// get the content reader
			ContentReader reader = contentService.getReader(nodeRef, property);

			// get the writer and set it up for text convert
			ContentWriter writer = contentService.getWriter(null, ContentModel.PROP_CONTENT, true);
			writer.setMimetype("text/plain");
			writer.setEncoding(reader.getEncoding());

			// try and transform the content
			if (contentService.isTransformable(reader, writer)) {
				contentService.transform(reader, writer);

				ContentReader resultReader = writer.getReader();
				if (resultReader != null && reader.exists()) {
					if (length != -1) {
						result = getContentString(resultReader, length);
					} else {
						result = resultReader.getContentString();
					}

				}
			}
			return result;
		}

		private String getContentTextViaTika(final NodeRef nodeRef, int length) {
			// get the content reader
			String result = null;
			ContentReader reader = contentService.getReader(nodeRef, property);

			ParsingReader parsingReader = null;
			try {
				parsingReader = new ParsingReader(reader.getContentInputStream());
				result = getContentString(parsingReader, length);

			} catch (ContentIOException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			} finally {
				if (parsingReader != null) {
					try {
						parsingReader.close();
					} catch (IOException e) {
						logger.error(e);
					}
				}
			}
			return result;
		}

		private String getContentString(ParsingReader parsingReader, int length) throws ContentIOException {
			if (length < 0 || length > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("Character count must be positive and within range");
			}
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(parsingReader);
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null && stringBuilder.length() < length) {
					stringBuilder.append(line + "\n");
				}
				return stringBuilder.toString();
			} catch (IOException e) {
				logger.error(e);
				throw new ContentIOException(
						"Failed to copy content to string: \n" + "   accessor: " + this + "\n" + "   length: " + length,
						e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Throwable e) {
						logger.error(e);
					}
				}
			}

		}

		private String getContentString(ContentReader contentReader, int length) throws ContentIOException {
			if (length < 0 || length > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("Character count must be positive and within range");
			}
			BufferedReader reader = null;
			try {
				String encoding = contentReader.getEncoding();
				// create a reader from the input stream
				if (encoding == null) {
					reader = new BufferedReader(new InputStreamReader(contentReader.getContentInputStream()));
				} else {
					reader = new BufferedReader(new InputStreamReader(contentReader.getContentInputStream(), encoding));
				}
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null && stringBuilder.length() < length) {
					stringBuilder.append(line + "\n");
				}
				return stringBuilder.toString();
			} catch (IOException e) {
				throw new ContentIOException(
						"Failed to copy content to string: \n" + "   accessor: " + this + "\n" + "   length: " + length,
						e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Throwable e) {
						logger.error(e);
					}
				}
			}
		}
	}
}
