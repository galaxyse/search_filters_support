/**
 *
 */
package com.arangodb.springframework.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.springframework.core.convert.ArangoConverter;
import com.arangodb.springframework.core.convert.ArangoCustomConversions;
import com.arangodb.springframework.core.convert.ArangoTypeMapper;
import com.arangodb.springframework.core.convert.DefaultArangoConverter;
import com.arangodb.springframework.core.convert.DefaultArangoTypeMapper;
import com.arangodb.springframework.core.convert.resolver.DefaultResolverFactory;
import com.arangodb.springframework.core.convert.resolver.ResolverFactory;
import com.arangodb.springframework.core.mapping.ArangoMappingContext;
import com.arangodb.springframework.core.template.ArangoTemplate;

/**
 * Defines methods to customize the Java-based configuration for Spring Data
 * ArangoDB.
 * 
 * @author Mark Vollmary
 *
 */
public interface ArangoConfiguration {

	ArangoDB.Builder arango();

	String database();

	@Bean
	default ArangoOperations arangoTemplate() throws Exception {
		return new ArangoTemplate(arango().build(), database(), arangoConverter(), resolverFactory());
	}

	@Bean
	default ArangoMappingContext arangoMappingContext() throws Exception {
		final ArangoMappingContext context = new ArangoMappingContext();
		context.setInitialEntitySet(getInitialEntitySet());
		context.setFieldNamingStrategy(fieldNamingStrategy());
		context.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
		return context;
	}

	@Bean
	default ArangoConverter arangoConverter() throws Exception {
		return new DefaultArangoConverter(arangoMappingContext(), customConversions(), resolverFactory(),
				arangoTypeMapper());
	}

	default CustomConversions customConversions() {
		return new ArangoCustomConversions(customConverters());
	}

	default Collection<Converter<?, ?>> customConverters() {
		return Collections.emptyList();
	}

	default Set<? extends Class<?>> getInitialEntitySet() throws ClassNotFoundException {
		return ArangoEntityClassScanner.scanForEntities(getEntityBasePackages());
	}

	default String[] getEntityBasePackages() {
		return new String[] { getClass().getPackage().getName() };
	}

	default FieldNamingStrategy fieldNamingStrategy() {
		return PropertyNameFieldNamingStrategy.INSTANCE;
	}

	default String typeKey() {
		return DefaultArangoTypeMapper.DEFAULT_TYPE_KEY;
	}

	default ArangoTypeMapper arangoTypeMapper() throws Exception {
		return new DefaultArangoTypeMapper(typeKey(), arangoMappingContext());
	}

	@Bean
	default ResolverFactory resolverFactory() {
		return RESOLVER_FACTORY_INSTANCE;
	}

	ResolverFactory RESOLVER_FACTORY_INSTANCE = new DefaultResolverFactory();

}