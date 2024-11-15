/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.koupleless.common;

import com.alipay.sofa.koupleless.common.exception.BizRuntimeException;
import com.alipay.sofa.koupleless.common.model.MainApplicationContext;
import com.alipay.sofa.koupleless.common.model.MainApplicationContextHolder;

import java.util.Map;

import static com.alipay.sofa.koupleless.common.exception.ErrorCodes.SpringContextManager.E100006;

/**
 * <p>MainApplication class.</p>
 *
 * @author lianglipeng.llp@alibaba-inc.com
 * @version $Id: MainApplication.java, v 0.1 2024年08月26日 00:34 立蓬 Exp $
 * @since 1.3.1
 */
public class MainApplication {
    /**
     * <p>init.</p>
     */
    public static void init() {
        BizRuntimeContext bizRuntimeContext = BizRuntimeContextRegistry
            .getBizRuntimeContextByClassLoader(Thread.currentThread().getContextClassLoader());

        if (null == bizRuntimeContext.getApplicationContext()) {
            bizRuntimeContext.setApplicationContext(
                new MainApplicationContextHolder(new MainApplicationContext()));
        }
    }

    private static MainApplicationContext get() {
        BizRuntimeContext bizRuntimeContext = BizRuntimeContextRegistry
            .getBizRuntimeContextByClassLoader(Thread.currentThread().getContextClassLoader());

        if (null != bizRuntimeContext.getApplicationContext()
            && bizRuntimeContext.getApplicationContext() instanceof MainApplicationContextHolder) {
            return ((MainApplicationContextHolder) bizRuntimeContext.getApplicationContext()).get();
        }
        return null;
    }

    /**
     * <p>getObject.</p>
     *
     * @param key a {@link java.lang.String} object
     * @return a {@link java.lang.Object} object
     */
    public static Object getObject(String key) {
        checkMainApplicationContext();
        return get().getObject(key);
    }

    /**
     * <p>getObjectMap.</p>
     *
     * @param type a {@link java.lang.Class} object
     * @param <T> a T class
     * @return a {@link java.util.Map} object
     */
    public static <T> Map<String, T> getObjectMap(Class<T> type) {
        checkMainApplicationContext();
        return get().getObjectMap(type);
    }

    /**
     * <p>register.</p>
     *
     * @param obj a {@link java.lang.Object} object
     */
    public static void register(Object obj) {
        checkMainApplicationContext();
        get().register(obj);
    }

    /**
     * <p>register.</p>
     *
     * @param alias a {@link java.lang.String} object
     * @param obj a {@link java.lang.Object} object
     */
    public static void register(String alias, Object obj) {
        checkMainApplicationContext();
        get().register(alias, obj);
    }

    private static void checkMainApplicationContext() {
        if (null == get()) {
            throw new BizRuntimeException(E100006,
                "mainApplicationContext is null! please exec MainApplicationContext.init() first.");
        }
    }
}
