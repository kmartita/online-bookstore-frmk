package com.allwyn;

import com.allwyn.tools.data.bodyschema.BookFields;
import com.allwyn.tools.data.generation.model.TestData;

import static com.allwyn.tools.ResourceUtil.getRequiredFields;

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
