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
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.superbiz;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Color implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;
    private int r;
    private int g;
    private int b;

    public Color() {
    }

    public Color(final String name, final int r, final int g, final int b) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getR() {
        return r;
    }

    public void setR(final int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(final int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(final int b) {
        this.b = b;
    }
}
