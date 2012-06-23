package com.brigitte.lookitup.jsf;

import com.brigitte.lookitup.jpa.controllers.AnalysisRecordJpaController;
import com.brigitte.lookitup.jpa.controllers.exceptions.IllegalOrphanException;
import com.brigitte.lookitup.jpa.controllers.exceptions.NonexistentEntityException;
import com.brigitte.lookitup.jpa.entities.AnalysisRecord;
import com.brigitte.lookitup.jsf.util.JsfUtil;
import com.brigitte.lookitup.jsf.util.PagingInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 *
 * @author mbohm
 */
public class AnalysisRecordController {

    public AnalysisRecordController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AnalysisRecordJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "analysisRecordJpa");
        pagingInfo = new PagingInfo();
        converter = new AnalysisRecordConverter();
    }
    private AnalysisRecord analysisRecord = null;
    private List<AnalysisRecord> analysisRecordItems = null;
    private AnalysisRecordJpaController jpaController = null;
    private AnalysisRecordConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getAnalysisRecordCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getAnalysisRecordItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAnalysisRecordEntities(), false);
    }

    public SelectItem[] getAnalysisRecordItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAnalysisRecordEntities(), true);
    }

    public AnalysisRecord getAnalysisRecord() {
        if (analysisRecord == null) {
            analysisRecord = (AnalysisRecord) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAnalysisRecord", converter, null);
        }
        if (analysisRecord == null) {
            analysisRecord = new AnalysisRecord();
        }
        return analysisRecord;
    }

    public String listSetup() {
        reset(true);
        return "analysisRecord_list";
    }

    public String createSetup() {
        reset(false);
        analysisRecord = new AnalysisRecord();
        return "analysisRecord_create";
    }

    public String create() {
        try {
            jpaController.create(analysisRecord);
            JsfUtil.addSuccessMessage("AnalysisRecord was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("analysisRecord_detail");
    }

    public String editSetup() {
        return scalarSetup("analysisRecord_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        analysisRecord = (AnalysisRecord) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAnalysisRecord", converter, null);
        if (analysisRecord == null) {
            String requestAnalysisRecordString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisRecord");
            JsfUtil.addErrorMessage("The analysisRecord with id " + requestAnalysisRecordString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String analysisRecordString = converter.getAsString(FacesContext.getCurrentInstance(), null, analysisRecord);
        String currentAnalysisRecordString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisRecord");
        if (analysisRecordString == null || analysisRecordString.length() == 0 || !analysisRecordString.equals(currentAnalysisRecordString)) {
            String outcome = editSetup();
            if ("analysisRecord_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit analysisRecord. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(analysisRecord);
            JsfUtil.addSuccessMessage("AnalysisRecord was successfully updated.");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisRecord");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("AnalysisRecord was successfully deleted.");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<AnalysisRecord> getAnalysisRecordItems() {
        if (analysisRecordItems == null) {
            getPagingInfo();
            analysisRecordItems = jpaController.findAnalysisRecordEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return analysisRecordItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "analysisRecord_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "analysisRecord_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        analysisRecord = null;
        analysisRecordItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        AnalysisRecord newAnalysisRecord = new AnalysisRecord();
        String newAnalysisRecordString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAnalysisRecord);
        String analysisRecordString = converter.getAsString(FacesContext.getCurrentInstance(), null, analysisRecord);
        if (!newAnalysisRecordString.equals(analysisRecordString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
