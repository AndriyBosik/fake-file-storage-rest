package com.example.file.storage.document;

import com.example.file.storage.config.Indexes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indexes.FILES)
public class File {

    @Id
    @Field(type = FieldType.Keyword, name = "id")
    private String id = "";

    @NotNull
    @NotEmpty
    @Field(type = FieldType.Text, name = "name")
    private String name;

    @PositiveOrZero
    @Field(type = FieldType.Integer, name = "size")
    private Integer size;

    private String[] tags = new String[0];
}
