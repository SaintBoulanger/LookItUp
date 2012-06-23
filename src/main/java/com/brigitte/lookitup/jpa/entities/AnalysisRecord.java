package com.brigitte.lookitup.jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author mbohm
 */
@Entity
@Table(name = "ANALYSIS_RECORD")
@NamedQueries({@NamedQuery(name = "AnalysisRecord.findAll", query = "SELECT c FROM AnalysisRecord c"), 
@NamedQuery(name = "AnalysisRecord.findByAnalysisRecordId", query = "SELECT c FROM AnalysisRecord c WHERE c.analysisRecordId = :analysisRecordId"), 
@NamedQuery(name = "AnalysisRecord.findByName", query = "SELECT c FROM AnalysisRecord c WHERE c.name = :name")})
public class AnalysisRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ANALYSISRECORD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer analysisRecordId;
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analysisRecordId")
    private Collection<AnalysisExpression> analysisExpressionCollection;

    public AnalysisRecord() {
    }

    public AnalysisRecord(Integer analysisRecordId) {
        this.analysisRecordId = analysisRecordId;
    }

    public Integer getAnalysisRecordId() {
        return analysisRecordId;
    }

    public void setAnalysisRecordId(Integer analysisRecordId) {
        this.analysisRecordId = analysisRecordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<AnalysisExpression> getAnalysisExpressionCollection() {
        return analysisExpressionCollection;
    }

    public void setAnalysisExpressionCollection(Collection<AnalysisExpression> analysisExpressionCollection) {
        this.analysisExpressionCollection = analysisExpressionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (analysisRecordId != null ? analysisRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalysisRecord)) {
            return false;
        }
        AnalysisRecord other = (AnalysisRecord) object;
        if ((this.analysisRecordId == null && other.analysisRecordId != null) || (this.analysisRecordId != null && !this.analysisRecordId.equals(other.analysisRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.AnalysisRecord[analysisRecordId=" + analysisRecordId + "]";
    }

}
