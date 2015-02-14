/*
 * #%L
 * Software Transansactional Memory Example
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.concurrency.part3;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class StmAccount {

  private final TxnLong lastUpdate;
  private final TxnInteger balance;

  public StmAccount(final int balance) {
    this.lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public int getBalance() {
    return balance.get();
  }

  public long getLastUpdate() {
    return lastUpdate.get();
  }

  @Override
  public String toString() {
    final String[] formatted = new String[1];
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        formatted[0] = String.format("%d (as of %tF %<tT)", balance.get(), lastUpdate.get());
      }
    });
    return formatted[0];
  }
}