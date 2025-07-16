package com.kmartita;

import com.kmartita.server.ApiRequestExecutor;
import com.kmartita.tools.data.Entity;
import com.kmartita.tools.data.HasId;
import com.kmartita.tools.helpers.EntityService;
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