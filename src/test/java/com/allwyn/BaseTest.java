package com.allwyn;

import com.allwyn.server.ApiRequestExecutor;
import com.allwyn.tool.data.Entity;
import com.allwyn.tool.data.HasId;
import com.allwyn.tool.helper.EntityService;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.List;

@Listeners({ AllureTestNg.class })
public class BaseTest {

    protected ApiRequestExecutor apiService;
    protected EntityService entityService;

    @BeforeClass(alwaysRun = true)
    public void init() {
        apiService = new ApiRequestExecutor();
        entityService = new EntityService();
    }

    protected <E extends HasId> void deleteEntities(List<E> entities, Entity entity) {
        if (!entities.isEmpty()) {
            entities.stream()
                    .map(E::getId)
                    .forEach(id -> apiService.delete(entity, id));
        }
    }
}