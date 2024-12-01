package com.bantu.springDemo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    @JsonProperty("key")
    private String key;
    @JsonProperty("value")
    private String value;
}
