package com.brigitte.lookitup.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author mbohm
 */
@Entity
@Table(name = "ANALYSIS_EXPRESSION")
@NamedQueries({@NamedQuery(name = "AnalysisExpression.findAll", query = "SELECT p FROM AnalysisExpression p"), 
@NamedQuery(name = "AnalysisExpression.findById", query = "SELECT p FROM AnalysisExpression p WHERE p.id = :id"), 
@NamedQuery(name = "AnalysisExpression.findByExpression", query = "SELECT p FROM AnalysisExpression p WHERE p.expression = :expression")})
public class AnalysisExpression implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "expression")
    private String expression;
    @JoinColumn(name = "RECORD_RECORD_ID", referencedColumnName = "ANALYSISRECORD_ID")
    @ManyToOne(optional = false)
    private AnalysisRecord analysisRecordId;

    public AnalysisExpression() {
    }

    public AnalysisExpression(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public AnalysisRecord getAnalysisRecordId() {
        return analysisRecordId;
    }

    public void setAnalysisRecordId(AnalysisRecord analysisRecordId) {
        this.analysisRecordId = analysisRecordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalysisExpression)) {
            return false;
        }
        AnalysisExpression other = (AnalysisExpression) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.AnalysisExpression[id=" + id + "]";
    }
}
