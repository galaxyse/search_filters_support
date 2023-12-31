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

package com.arangodb.springframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define given fields to be indexed using ArangoDB's Skiplist
 * index.
 *
 * @author Mark Vollmary
 * @deprecated use {@link PersistentIndex} instead. Since ArangoDB 3.7 a skiplist index is an alias for a persistent index.
 *
 */
@Repeatable(SkiplistIndexes.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Deprecated
public @interface SkiplistIndex {

	/**
	 * A list of attribute paths
	 */
	String[] fields();

	/**
	 * If {@literal true}, then create a unique index
	 */
	boolean unique() default false;

	/**
	 * If {@literal true}, then create a sparse index
	 */
	boolean sparse() default false;

	/**
	 * If {@literal false}, the deduplication of array values is turned off.
	 */
	boolean deduplicate() default true;

}
