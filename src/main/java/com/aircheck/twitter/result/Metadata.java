
package com.aircheck.twitter.result;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "iso_language_code",
    "result_type"
})
public class Metadata {

    @JsonProperty("iso_language_code")
    private String isoLanguageCode;
    @JsonProperty("result_type")
    private String resultType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The isoLanguageCode
     */
    @JsonProperty("iso_language_code")
    public String getIsoLanguageCode() {
        return isoLanguageCode;
    }

    /**
     * 
     * @param isoLanguageCode
     *     The iso_language_code
     */
    @JsonProperty("iso_language_code")
    public void setIsoLanguageCode(String isoLanguageCode) {
        this.isoLanguageCode = isoLanguageCode;
    }

    /**
     * 
     * @return
     *     The resultType
     */
    @JsonProperty("result_type")
    public String getResultType() {
        return resultType;
    }

    /**
     * 
     * @param resultType
     *     The result_type
     */
    @JsonProperty("result_type")
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
