package com.sourcesense.crl.job;


public class Constant {

    public static String QUERY_CURRENT_LEGISLATURE = "SELECT MAX(id_legislature) FROM legislature";
    public static String COLUMN_ID_LEGISLATURE = "id_legislature";

    public static String QUERY_ALL_COUNCILORS_FILTERED_BY_LEGISLATURE
                         = "SELECT DISTINCT ll.num_legislatura, " +
                                           "pp.cognome, " +
                                           "pp.nome, " +
                                           "gg.nome_gruppo, " +
                                           "( " +
                                             "STUFF( " +
                                                    "( " +
                                                       "SELECT ', ' + oo.nome_organo " +
                                                       "FROM persona ppo " +
                                                       "INNER JOIN join_persona_organo_carica AS jpoc " +
                                                               "ON ppo.id_persona = jpoc.id_persona " +
                                                       "INNER JOIN legislature ll " +
                                                               "ON ll.id_legislatura = jpoc.id_legislatura " +
                                                       "INNER JOIN organi oo " +
                                                               "ON oo.id_organo = jpoc.id_organo " +
                                                       "WHERE ppo.deleted = 0 " +
                                                         "AND oo.deleted = 0 " +
                                                         "AND jpoc.deleted = 0 " +
                                                         "AND jpoc.id_legislatura = ? " +
                                                         "AND jpoc.data_fine is null " +
                                                         "AND ppo.id_persona = pp.id_persona " +
                                                       "ORDER BY oo.nome_organo " +
                                                       "FOR XML PATH(''), TYPE " +
                                                     ").value('.','varchar(max)') " +
                                             ",1,2, '') " +
                                           ") as Organi " +
                           "FROM persona AS pp " +
                           "INNER JOIN join_persona_gruppi_politici jpg " +
                                   "ON pp.id_persona = jpg.id_persona " +
                           "INNER JOIN gruppi_politici gg " +
                                   "ON gg.id_gruppo = jpg.id_gruppo " +
                           "INNER JOIN join_persona_organo_carica AS jpoc " +
                                   "ON pp.id_persona = jpoc.id_persona " +
                           "INNER JOIN legislature ll " +
                                   "ON ll.id_legislatura = jpoc.id_legislatura " +
                           "INNER JOIN organi oo " +
                                   "ON oo.id_organo = jpoc.id_organo " +
                           "WHERE pp.deleted = 0 " +
                             "AND jpoc.deleted = 0 " +
                             "AND pp.deleted = 0 " +
                             "AND jpg.deleted = 0 " +
                             "AND gg.deleted = 0 " +
                             "AND oo.deleted = 0 " +
                             "AND jpg.data_fine is null " +
                             "AND jpoc.data_fine is null " +
                             "AND ll.id_legislatura = ? " +
                           "ORDER BY cognome, nome";
    public static String COLUMN_NOME = "nome";
    public static String COLUMN_COGNOME = "cognome";
    public static String COLUMN_NOME_GRUPPO = "nome_gruppo";
    public static String COLUMN_NUMERO_LEGISLATURA = "num_legislatura";
    public static String COLUMN_ORGANI = "Organi";
}
