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

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Startup
@Lock(READ)
@Singleton
@Path("/color")
@CacheDefaults(cacheName = "tomee")
public class ColorService implements MessageListener<Object> {

    @Inject
    private Cache<String, Color> cache;

    @Inject
    @ConfigProperty(name = "color.of.money", defaultValue = "white")
    private String color;
    private ITopic<Object> tomee;
    private HazelcastInstance instance;

    @PostConstruct
    public void postConstruct() {
        instance = Hazelcast.newHazelcastInstance(new ClasspathXmlConfig("hazelcast.xml"));
        tomee = instance.getReliableTopic("tomee");
        tomee.addMessageListener(this);
    }

    @PreDestroy
    public void preDestroy() {
        try {
            instance.shutdown();
        } catch (final Exception ignore) {
            //no-op
        }
    }

    @GET
    public String getColor() {
        return color;
    }

    @Lock(WRITE)
    @Path("{color}")
    @POST
    @CacheRemove
    public void setColor(@PathParam("color") final String color) {
        this.color = color;
        tomee.publish(color);
    }

    @Path("object")
    @CacheResult
    @GET
    @Produces({APPLICATION_JSON})
    public Color getColorObject(@QueryParam(value = "color") @CacheKey final String color) {
        return new Color(color, 0xE7, 0x71, 0x00);
    }

    @Override
    public void onMessage(final Message<Object> message) {
        this.color = String.class.cast(message.getMessageObject());

        System.out.println("\n\n\n\ncolor of money is " + this.color + "\n\n\n\n\n");
    }
}
