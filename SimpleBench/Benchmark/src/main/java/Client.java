import cn.edu.thssdb.rpc.thrift.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client{
    private IService.Client client;
    private TTransport transport;
    private TProtocol protocol;

    private String userName = "root";

    private String password = "root";

    private long sessionId;
    public Client(String host, int port) throws TException {
        transport = new TSocket(host, port);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        client = new IService.Client(protocol);

        ConnectResp resp = client.connect(new ConnectReq(userName,password));
        sessionId = resp.sessionId;
    }

    public ExecuteStatementResp executeStatement(String sql) throws TException {
        return client.executeStatement(new ExecuteStatementReq(sessionId,sql));
    }

    public void close(){
        DisconnetReq req = new DisconnetReq(sessionId);
        try {
            client.disconnect(req);
        } catch (TException e) {
            e.printStackTrace();
        }
        transport.close();
    }


}