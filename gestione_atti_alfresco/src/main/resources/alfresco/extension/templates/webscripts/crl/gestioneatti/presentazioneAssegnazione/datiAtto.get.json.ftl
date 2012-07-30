{
   "Atto": 
   {
	"id":"${atto.nodeRef}",
	"numeroAtto":"<#if atto.properties["crlatti:numeroAtto"]?exists>${atto.properties["crlatti:numeroAtto"]}<#else></#if>",
	"classificazione":"<#if atto.properties["crlatti:classificazione"]?exists>${atto.properties["crlatti:classificazione"]}<#else></#if>",
	"oggetto":"<#if atto.properties["crlatti:oggetto"]?exists>${atto.properties["crlatti:oggetto"]}<#else></#if>",
	"numeroRepertorio":"<#if atto.properties["crlatti:numeroRepertorio"]?exists>${atto.properties["crlatti:numeroRepertorio"]}<#else></#if>",
	"dataRepertorio":"<#if atto.properties["crlatti:dataRepertorio"]?exists>${atto.properties["crlatti:dataRepertorio"]?string("dd/MM/yyyy")}<#else></#if>",
	"tipoIniziativa":"<#if atto.properties["crlatti:tipoIniziativa"]?exists>${atto.properties["crlatti:tipoIniziativa"]}<#else></#if>",
	"dataIniziativa":"<#if atto.properties["crlatti:dataIniziativa"]?exists>${atto.properties["crlatti:dataIniziativa"]?string("dd/MM/yyyy")}<#else></#if>",
	"descrizioneIniziativa":"<#if atto.properties["crlatti:descrizioneIniziativa"]?exists>${atto.properties["crlatti:descrizioneIniziativa"]}<#else></#if>",
	"assegnazione":"<#if atto.properties["crlatti:assegnazione"]?exists>${atto.properties["crlatti:assegnazione"]}<#else></#if>"
	"numeroDgr":"<#if atto.properties["crlatti:numeroDgr"]?exists>${atto.properties["crlatti:numeroDgr"]}<#else></#if>",
	"dataDgr":"<#if atto.properties["crlatti:dataDgr"]?exists>${atto.properties["crlatti:dataDgr"]?string("dd/MM/yyyy")}<#else></#if>"
   }
}