package org.apache.mesos.mini;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class MesosClusterTest {

    @ClassRule
    public static MesosCluster cluster = new MesosCluster(MesosClusterConfig.builder()
            .numberOfSlaves(3)
            .privateRegistryPort(15000) // Currently you have to choose an available port by yourself
            .slaveResources(new String[]{"ports(*):[9200-9200,9300-9300]", "ports(*):[9201-9201,9301-9301]", "ports(*):[9202-9202,9302-9302]"})
//            .imagesToBuild(new ImageToBuild(new File("executor"), "mesos/elasticsearch-executor"))
//            .dockerInDockerImages(new String[]{"mesos/elasticsearch-executor"})
            .build());


    @Test
    public void mesosClusterCanBeStarted() throws Exception {

        JSONObject stateInfo = cluster.getStateInfoJSON();

        Assert.assertEquals(3, stateInfo.getInt("activated_slaves"));
    }

    @Test
    public void mesosClusterCanBeStarted2() throws Exception {

         JSONObject stateInfo = cluster.getStateInfoJSON();
        Assert.assertEquals(3, stateInfo.getInt("activated_slaves"));


        String mesosMasterUrl = cluster.getMesosMasterURL();
        Assert.assertTrue(mesosMasterUrl.contains(":5050"));
    }

}