package test;

import com.containersol.minimesos.cluster.MesosCluster;
import com.containersol.minimesos.mesos.ClusterArchitecture;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class MesosClusterTest {
    @ClassRule
    public static MesosCluster cluster = new MesosCluster(new ClusterArchitecture.Builder()
            .withZooKeeper()
            .withMaster()
            .withAgent("ports(*):[9200-9200,9300-9300]")
            .withAgent("ports(*):[9201-9201,9301-9301]")
            .withAgent("ports(*):[9202-9202,9302-9302]")
            .build());

    @Test
    public void mesosClusterCanBeStarted() throws Exception {
        JSONObject stateInfo = cluster.getClusterStateInfo();
        Assert.assertEquals(3, stateInfo.getInt("activated_slaves"));
        Assert.assertTrue(cluster.getMasterContainer().getStateUrl().contains(":5050"));
    }
}