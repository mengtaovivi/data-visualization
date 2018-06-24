package com.taikang.dataVis.domain;

import java.io.Serializable;
import org.springframework.validation.annotation.Validated;

@Validated
public class Dict implements Serializable{


	private static final long serialVersionUID = 1L;

    private Long id;

    private String dictCode;

    private String dictValue;

    private String dictName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
}
