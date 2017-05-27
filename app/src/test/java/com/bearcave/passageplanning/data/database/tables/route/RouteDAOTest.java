package com.bearcave.passageplanning.data.database.tables.route;

import com.bearcave.passageplanning.routes.database.route.RouteDAO;

import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;  // main one

import java.util.ArrayList;

/**
 * Created by miwas on 24.05.17.
 */
public class RouteDAOTest {


    @Test
    public void fromStringToList() throws Exception {
        String ids = "1,2,3,4,";

        ArrayList<Integer> result = RouteDAO.fromString(ids);
        assertThat(result)
                .hasSize(4)
                .contains(1, 2, 3, 4)
                .doesNotContain(5, 6)
        ;

    }

    @Test
    public void fromListToString() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);

        Assert.assertEquals("1,2,3,4,", RouteDAO.fromList(ids));
    }


}