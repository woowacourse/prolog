package wooteco.prolog.common;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@Profile({"local", "test"})
@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchLocalConfig extends AbstractElasticsearchConfiguration {

    public static final String DOCKER_IMAGE_NAME = "docker.elastic.co/elasticsearch/elasticsearch:7.14.1";

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ElasticsearchContainer container = new ElasticsearchContainer(DOCKER_IMAGE_NAME);
        container.start();

        BasicCredentialsProvider credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(AuthScope.ANY,
                                          new UsernamePasswordCredentials("admin", "admin"));

        RestClientBuilder builder = RestClient
            .builder(HttpHost.create(container.getHttpHostAddress()))
            .setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder
                    .setDefaultCredentialsProvider(credentialProvider)
            );

        return new RestHighLevelClient(builder);
    }

}
