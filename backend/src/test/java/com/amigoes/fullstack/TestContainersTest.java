package com.amigoes.fullstack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// for unit test we should not use ever the @SpringBootTest Annotation
public class TestContainersTest extends AbstractTestcontainersUnitTest {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }

}
