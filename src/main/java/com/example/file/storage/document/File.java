package com.example.file.storage.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "files")
public class File {

    @Id
    @Field(type = FieldType.Keyword, name = "id")
    private String id = "";

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Integer, name = "size")
    private Integer size;

    private String[] tags = new String[0];
}
