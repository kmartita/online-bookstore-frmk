package com.allwyn;

import com.allwyn.server.ApiRequestExecutor;
import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.HasId;
import com.allwyn.tools.helpers.EntityService;
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
