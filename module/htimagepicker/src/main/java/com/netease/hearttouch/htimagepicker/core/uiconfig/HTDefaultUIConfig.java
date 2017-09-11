/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.uiconfig;

/**
 * Created by zyl06 on 3/21/16.
 */
public class HTDefaultUIConfig extends BaseUIConfig {
    public HTDefaultUIConfig() {
        super();
    }

    public static DefaultUIConfigBuilder newBuilder() {
        return new DefaultUIConfigBuilder();
    }

    public static class DefaultUIConfigBuilder extends UIConfigBuilder<HTDefaultUIConfig> {
        private DefaultUIConfigBuilder() {
            super(new HTDefaultUIConfig());
        }
    }
}
