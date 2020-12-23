wildfly Config JMS:
        <extension module="org.wildfly.extension.messaging-activemq"/>


        <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
            <server name="default">
                <security-setting name="#">
                    <role name="guest" delete-non-durable-queue="true" create-non-durable-queue="true" consume="true" send="true"/>
                </security-setting>
                <address-setting name="#" message-counter-history-day-limit="10" page-size-bytes="2097152" max-size-bytes="10485760" expiry-address="jms.queue.ExpiryQueue" dead-letter-address="jms.queue.DLQ"/>
                <http-connector name="http-connector" endpoint="http-acceptor" socket-binding="http"/>
                <http-connector name="http-connector-throughput" endpoint="http-acceptor-throughput" socket-binding="http">
                    <param name="batch-delay" value="50"/>
                </http-connector>
                <in-vm-connector name="in-vm" server-id="0"/>
                <http-acceptor name="http-acceptor" http-listener="default"/>
                <http-acceptor name="http-acceptor-throughput" http-listener="default">
                    <param name="batch-delay" value="50"/>
                    <param name="direct-deliver" value="false"/>
                </http-acceptor>
                <in-vm-acceptor name="in-vm" server-id="0"/>
                <jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/>
                <jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/>
                <jms-queue name="TasksQueue" entries="queue/TasksQueue"/>
                <connection-factory name="InVmConnectionFactory" entries="java:/ConnectionFactory" connectors="in-vm"/>
                <connection-factory name="RemoteConnectionFactory" entries="java:jboss/exported/jms/RemoteConnectionFactory" connectors="http-connector"/>
                <pooled-connection-factory name="activemq-ra" transaction="xa" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm"/>
            </server>
        </subsystem>

