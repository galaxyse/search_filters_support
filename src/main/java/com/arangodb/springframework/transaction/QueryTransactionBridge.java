/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.springframework.transaction;

import com.arangodb.ArangoDBException;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.transaction.TransactionSystemException;

import java.util.Collection;

/**
 * Bridge to postpone late transaction start to be able to inject collections from query side.
 */
public class QueryTransactionBridge {
    private static final ThreadLocal<ArangoTransactionObject> CURRENT_TRANSACTION = new NamedInheritableThreadLocal<>("ArangoTransactionBegin");

    public void setCurrentTransaction(ArangoTransactionObject transactionObject) {
        CURRENT_TRANSACTION.set(transactionObject);
    }

    public void clearCurrentTransaction() {
        CURRENT_TRANSACTION.remove();
    }

    public String getCurrentTransaction(Collection<String> collections) {
        ArangoTransactionObject tx = CURRENT_TRANSACTION.get();
        if (tx == null) {
            return null;
        }

        try {
            return tx.getOrBegin(collections).getStreamTransactionId();
        } catch (ArangoDBException error) {
            throw new TransactionSystemException("Cannot begin transaction", error);
        }
    }
}
