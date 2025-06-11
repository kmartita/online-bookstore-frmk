package com.allwyn;

import com.allwyn.tool.data.bodyschema.BookFields;
import com.allwyn.tool.data.generation.model.TestData;

import static com.allwyn.tool.ResourceUtil.getRequiredFields;

public class Demo {

    public static void main(String[] args) {
        TestData<BookFields> requiredData = TestData
                .preGenerate(getRequiredFields(BookFields.class))
                .build();

        TestData<BookFields> data = TestData
                .preGenerate(getRequiredFields(BookFields.class))
                .setField(BookFields.DESCRIPTION, BookFields.DESCRIPTION.generate())
                .build()
                .edit(BookFields.COMPLETED, false);

        System.out.println(data);
    }
}
