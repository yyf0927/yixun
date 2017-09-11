/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.uiconfig;

/**
 * Created by zyl06 on 3/21/16.
 */
public class HTRuntimeUIConfig extends BaseUIConfig{
    public HTRuntimeUIConfig() {
        super();
    }

    public static RuntimeUIConfigBuilder newBuilder() {
        return new RuntimeUIConfigBuilder();
    }

    public static class RuntimeUIConfigBuilder extends UIConfigBuilder<HTRuntimeUIConfig> {
        private RuntimeUIConfigBuilder() {
            super(new HTRuntimeUIConfig());
        }
    }
}
