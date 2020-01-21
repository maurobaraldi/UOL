package br.com.uol.cotacoes.acoes;

import br.com.uol.cotacoes.ContextTest;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vrx_mtoledo on 10/07/17.
 */
@Profile("test")
public class ListaIndicesTrimestralSteps {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContextTest contextTest;

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
    private String yesterday = today.minusDays(1).format(dateFormat);
    private String twoDaysBefore = today.minusDays(2).format(dateFormat);
    private String oneWeekBefore = today.minusDays(7).format(dateFormat);
    private String oneWeekAndOneDayBefore = today.minusDays(7).minusDays(1).format(dateFormat);
    private String oneMonthBefore = today.minusMonths(1).format(dateFormat);
    private String oneMonthAndOneDayBefore = today.minusMonths(1).minusDays(1).format(dateFormat);
    private String threeMonthsBefore = today.minusMonths(3).format(dateFormat);

    private DateTimeFormatter dateFormatCSV = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private String yesterdayCSV = today.minusDays(1).format(dateFormatCSV);
    private String twoDaysBeforeCSV = today.minusDays(2).format(dateFormatCSV);
    private String oneWeekBeforeCSV = today.minusDays(7).format(dateFormatCSV);
    private String oneWeekAndOneDayBeforeCSV = today.minusDays(7).minusDays(1).format(dateFormatCSV);
    private String oneMonthBeforeCSV = today.minusMonths(1).format(dateFormatCSV);
    private String oneMonthAndOneDayBeforeCSV = today.minusMonths(1).minusDays(1).format(dateFormatCSV);
    private String threeMonthsBeforeCSV = today.minusMonths(3).format(dateFormatCSV);

    @Quando("^processamos a solicitação de Lista de Indices Trimestral$")
    public void processamos_a_solicitação_de_Lista_de_Indices_Trimestral() throws Throwable {
        StringBuilder url = new StringBuilder("/index/interday/list/months?")
                .append(contextTest.getParameterJsonp())
                .append("&")
                .append(contextTest.getParameterItem())
                .append("&")
                .append(contextTest.getParemeterFields())
                .append("&")
                .append(contextTest.getParameterFormat());
        contextTest.setResultActions(this.mockMvc.perform(get(url.toString())));
    }

    @Entao("^deve exibir uma lista com a funcao de callback contendo o JSON dos campos enviados da Lista de Indices Trimestral$")
    public void deve_exibir_uma_lista_com_a_funcao_de_callback_contendo_o_JSON_dos_campos_enviados_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expected = "/**/list({\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"ask\":0,\"change\":41.398,\"pctChange\":0.33}]});";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir uma lista com a funcao de callback contendo o JSON dos campos enviados com novo valor no topo da Lista de Indices Trimestral$")
    public void deve_exibir_uma_lista_com_a_funcao_de_callback_contendo_o_JSON_dos_campos_enviados_com_novo_valor_no_topo_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "/**/list({\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                yesterday +
                "\",\"price\":5.55,\"exchangeasset\":\"INDX IND\",\"high\":5.66,\"low\":5.77,\"open\":5.88,\"volume\":100,\"close\":5.99,\"bid\":5.56,\"ask\":5.67,\"change\":-0.05,\"pctChange\":-1.55},{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"ask\":0,\"change\":41.398,\"pctChange\":0.33}]});";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um JSON dos campos enviados da Lista de Indices Trimestral$")
    public void deve_exibir_um_JSON_dos_campos_enviados_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"ask\":0,\"change\":41.398,\"pctChange\":0.33}]}";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir uma lista com a funcao de callback contendo o JSON dos campos enviados sem o campo ASKED \\(campo deve ser ignorado\\) da Lista de Indices Trimestral$")
    public void deve_exibir_uma_lista_com_a_funcao_de_callback_contendo_o_JSON_dos_campos_enviados_sem_o_campo_ASKED_campo_deve_ser_ignorado_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "/**/list({\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"change\":41.398,\"pctChange\":0.33}]});";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um CSV contendo os campos enviados da Lista de Indices Trimestral$")
    public void deve_exibir_um_CSV_contendo_os_campos_enviados_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "Data;Preço;Ativo;Máximo(R$);Mínimo(R$);Abertura(R$);Volume;Fechamento(R$);Venda;Compra;Variação(R$);Variação(%);\r\n" +
                twoDaysBeforeCSV +
                ";12561.680;INDX IND;12567.132;12561.680;12567.132;0.000;12567.022;0.000;0.000;-5.342;-0.04;\r\n" +
                oneWeekBeforeCSV +
                ";12558.717;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;0.000;-8.305;-0.07;\r\n" +
                oneWeekAndOneDayBeforeCSV +
                ";12562.851;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;0.000;-4.171;-0.03;\r\n" +
                oneMonthBeforeCSV +
                ";12554.574;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;0.000;-12.448;-0.10;\r\n" +
                oneMonthAndOneDayBeforeCSV +
                ";12565.147;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;0.000;-1.875;-0.01;\r\n" +
                threeMonthsBeforeCSV +
                ";12527.167;INDX IND;12542.208;12465.031;12485.535;0.000;12485.770;0.000;0.000;41.398;0.33;\r\n";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um CSV contendo os campos enviados com novo valor no topo da Lista de Indices Trimestral$")
    public void deve_exibir_um_CSV_contendo_os_campos_enviados_com_novo_valor_no_topo_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "Data;Preço;Ativo;Máximo(R$);Mínimo(R$);Abertura(R$);Volume;Fechamento(R$);Venda;Compra;Variação(R$);Variação(%);\r\n" +
                yesterdayCSV +
                ";5.550;INDX IND;5.660;5.770;5.880;100.000;5.990;5.560;5.670;-0.050;-1.55;\r\n" +
                twoDaysBeforeCSV +
                ";12561.680;INDX IND;12567.132;12561.680;12567.132;0.000;12567.022;0.000;0.000;-5.342;-0.04;\r\n" +
                oneWeekBeforeCSV +
                ";12558.717;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;0.000;-8.305;-0.07;\r\n" +
                oneWeekAndOneDayBeforeCSV +
                ";12562.851;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;0.000;-4.171;-0.03;\r\n" +
                oneMonthBeforeCSV +
                ";12554.574;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;0.000;-12.448;-0.10;\r\n" +
                oneMonthAndOneDayBeforeCSV +
                ";12565.147;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;0.000;-1.875;-0.01;\r\n" +
                threeMonthsBeforeCSV +
                ";12527.167;INDX IND;12542.208;12465.031;12485.535;0.000;12485.770;0.000;0.000;41.398;0.33;\r\n";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um CSV dos campos enviados sem o campo ASKED \\(campo deve ser ignorado\\) da Lista de Indices Trimestral$")
    public void deve_exibir_um_CSV_dos_campos_enviados_sem_o_campo_ASKED_campo_deve_ser_ignorado_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "Data;Preço;Ativo;Máximo(R$);Mínimo(R$);Abertura(R$);Volume;Fechamento(R$);Venda;Variação(R$);Variação(%);\r\n" +
                twoDaysBeforeCSV +
                ";12561.680;INDX IND;12567.132;12561.680;12567.132;0.000;12567.022;0.000;-5.342;-0.04;\r\n" +
                oneWeekBeforeCSV +
                ";12558.717;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;-8.305;-0.07;\r\n" +
                oneWeekAndOneDayBeforeCSV +
                ";12562.851;INDX IND;12567.132;12558.717;12567.132;0.000;12567.022;0.000;-4.171;-0.03;\r\n" +
                oneMonthBeforeCSV +
                ";12554.574;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;-12.448;-0.10;\r\n" +
                oneMonthAndOneDayBeforeCSV +
                ";12565.147;INDX IND;12567.132;12544.605;12567.132;0.000;12567.022;0.000;-1.875;-0.01;\r\n" +
                threeMonthsBeforeCSV +
                ";12527.167;INDX IND;12542.208;12465.031;12485.535;0.000;12485.770;0.000;41.398;0.33;\r\n";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um JSON dos campos enviados com novo valor no topo da Lista de Indices Trimestral$")
    public void deve_exibir_um_JSON_dos_campos_enviados_com_novo_valor_no_topo_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                yesterday +
                "\",\"price\":5.55,\"exchangeasset\":\"INDX IND\",\"high\":5.66,\"low\":5.77,\"open\":5.88,\"volume\":100,\"close\":5.99,\"bid\":5.56,\"ask\":5.67,\"change\":-0.05,\"pctChange\":-1.55},{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"ask\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"ask\":0,\"change\":41.398,\"pctChange\":0.33}]}";
        assertEquals(expected, result);
    }

    @Entao("^deve exibir um JSON dos campos enviados sem o campo ASKED \\(campo deve ser ignorado\\) da Lista de Indices Trimestral$")
    public void deve_exibir_um_JSON_dos_campos_enviados_sem_o_campo_ASKED_campo_deve_ser_ignorado_da_Lista_de_Indices_Trimestral() throws Throwable {
        String result = contextTest.getResultActions()
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"prev\":null,\"next\":null,\"docs\":[{\"date\":\"" +
                twoDaysBefore +
                "\",\"price\":12561.68,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12561.68,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-5.342,\"pctChange\":-0.04},{\"date\":\"" +
                oneWeekBefore +
                "\",\"price\":12558.717,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-8.305,\"pctChange\":-0.07},{\"date\":\"" +
                oneWeekAndOneDayBefore +
                "\",\"price\":12562.851,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12558.717,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-4.171,\"pctChange\":-0.03},{\"date\":\"" +
                oneMonthBefore +
                "\",\"price\":12554.574,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-12.448,\"pctChange\":-0.1},{\"date\":\"" +
                oneMonthAndOneDayBefore +
                "\",\"price\":12565.147,\"exchangeasset\":\"INDX IND\",\"high\":12567.132,\"low\":12544.605,\"open\":12567.132,\"volume\":0,\"close\":12567.022,\"bid\":0,\"change\":-1.875,\"pctChange\":-0.01},{\"date\":\"" +
                threeMonthsBefore +
                "\",\"price\":12527.167,\"exchangeasset\":\"INDX IND\",\"high\":12542.208,\"low\":12465.031,\"open\":12485.535,\"volume\":0,\"close\":12485.77,\"bid\":0,\"change\":41.398,\"pctChange\":0.33}]}";
        assertEquals(expected, result);
    }

}

