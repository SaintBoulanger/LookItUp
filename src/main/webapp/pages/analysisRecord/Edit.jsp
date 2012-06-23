<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing AnalysisRecord</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Editing AnalysisRecord</h1>
            <h:form>
                <h:panelGrid columns="1">
                    <h:outputText value="Name:"/>
                    <h:inputText id="name" value="#{analysisRecord.analysisRecord.name}" title="Name" />
                    <h:outputText value="AnalysisExpressionCollection:"/>
                    <h:selectManyListbox id="analysisExpressionCollection" value="#{analysisRecord.analysisRecord.jsfcrud_transform[jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToCollection].analysisExpressionCollection}" title="AnalysisExpressionCollection" size="6" converter="#{analysisExpression.converter}" >
                        <f:selectItems value="#{analysisExpression.analysisExpressionItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{analysisRecord.edit}" value="Save">
                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{analysisRecord.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{analysisRecord.listSetup}" value="Show All AnalysisRecord Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
