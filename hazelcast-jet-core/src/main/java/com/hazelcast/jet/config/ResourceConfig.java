/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.config;

import com.hazelcast.internal.util.Preconditions;
import com.hazelcast.spi.annotation.PrivateApi;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * Describes a single resource to deploy to the Jet cluster.
 *
 * @since 3.0
 */
@PrivateApi
public class ResourceConfig implements Serializable {

    private final URL url;
    private final String id;
    private final ResourceType resourceType;

    /**
     * Creates a resource config with the given properties.
     *
     * @param url           url of the resource
     * @param id            id of the resource
     * @param resourceType  type of the resource
     */
    ResourceConfig(@Nonnull URL url, @Nonnull String id, @Nonnull ResourceType resourceType) {
        Preconditions.checkNotNull(url, "url");
        Preconditions.checkNotNull(resourceType, "resourceType");
        Preconditions.checkHasText(id, "id cannot be null or empty");

        this.url = url;
        this.id = id;
        this.resourceType = resourceType;
    }

    /**
     * Creates a config for a class to be deployed. Derives the config
     * properties automatically.
     *
     * @param clazz the class to deploy
     */
    ResourceConfig(@Nonnull Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "clazz");

        String id = clazz.getName().replace('.', '/') + ".class";
        URL url = clazz.getClassLoader().getResource(id);
        if (url == null) {
            throw new IllegalArgumentException("Couldn't derive URL from class " + clazz);
        }

        this.id = id;
        this.url = url;
        this.resourceType = ResourceType.CLASS;
    }

    /**
     * Returns the URL at which the resource is available. Resolved on the
     * local machine during job submission.
     */
    @Nonnull
    public URL getUrl() {
        return url;
    }

    /**
     * Returns the ID of the resource that will be used to form the {@code
     * IMap} key under which it will be stored in the Jet cluster.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Returns the type of the resource.
     */
    @Nonnull
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public String toString() {
        return "ResourceConfig{" +
                "url=" + url +
                ", id='" + id + '\'' +
                ", resourceType=" + resourceType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResourceConfig that = (ResourceConfig) o;
        return url.toString().equals(that.url.toString()) &&
                id.equals(that.id) &&
                resourceType == that.resourceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, id, resourceType);
    }
}
