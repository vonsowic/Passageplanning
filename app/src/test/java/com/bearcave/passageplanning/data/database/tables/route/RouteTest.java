package com.bearcave.passageplanning.data.database.tables.route;

import com.bearcave.passageplanning.routes.database.Route;

import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;  // main one

import java.util.ArrayList;

/**
 * @author Michał Wąsowicz
 * @since  24.05.17.
 * @version 1.0
 */
public class RouteTest {


    @Test
    public void fromStringToList() throws Exception {
        String ids = "1,2,3,4,";

        ArrayList<Integer> result = Route.Companion.fromString(ids);
        //assertThat(result)
        //        .hasSize(4)
        //        .contains(1, 2, 3, 4)
        //        .doesNotContain(5, 6)
        //;

    }

    @Test
    public void fromListToString() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);

        Assert.assertEquals("1,2,3,4,", Route.Companion.fromList(ids));
    }


}