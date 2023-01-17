package com.arangodb.springframework;

import com.arangodb.springframework.transaction.ArangoTransactionManagementConfigurer;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Import(ArangoTransactionManagementConfigurer.class)
public class ArangoTransactionalTestConfiguration {

}
