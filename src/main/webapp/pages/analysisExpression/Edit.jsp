<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing AnalysisExpression</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Editing AnalysisExpression</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Expression:"/>
                    <h:inputText id="expression" value="#{analysisExpression.analysisExpression.expression}" title="Expression" />
                </h:panelGrid>
                <br />
                <h:commandLink action="#{analysisExpression.edit}" value="Save">
                    <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisExpression.analysisExpression][analysisExpression.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{analysisExpression.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisExpression.analysisExpression][analysisExpression.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{analysisExpression.listSetup}" value="Show All AnalysisExpression Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
