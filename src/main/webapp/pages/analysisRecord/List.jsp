<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing AnalysisRecord Items</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing AnalysisRecord Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No AnalysisRecord Items Found)<br />" rendered="#{analysisRecord.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{analysisRecord.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{analysisRecord.pagingInfo.firstItem + 1}..#{analysisRecord.pagingInfo.lastItem} of #{analysisRecord.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{analysisRecord.prev}" value="Previous #{analysisRecord.pagingInfo.batchSize}" rendered="#{analysisRecord.pagingInfo.firstItem >= analysisRecord.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{analysisRecord.next}" value="Next #{analysisRecord.pagingInfo.batchSize}" rendered="#{analysisRecord.pagingInfo.lastItem + analysisRecord.pagingInfo.batchSize <= analysisRecord.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{analysisRecord.next}" value="Remaining #{analysisRecord.pagingInfo.itemCount - analysisRecord.pagingInfo.lastItem}"
                                   rendered="#{analysisRecord.pagingInfo.lastItem < analysisRecord.pagingInfo.itemCount && analysisRecord.pagingInfo.lastItem + analysisRecord.pagingInfo.batchSize > analysisRecord.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{analysisRecord.analysisRecordItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Name"/>
                            </f:facet>
                            <h:outputText value=" #{item.name}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{analysisRecord.detailSetup}">
                                <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisRecord.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{analysisRecord.editSetup}">
                                <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisRecord.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{analysisRecord.destroy}">
                                <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisRecord.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{analysisRecord.createSetup}" value="New AnalysisRecord"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
