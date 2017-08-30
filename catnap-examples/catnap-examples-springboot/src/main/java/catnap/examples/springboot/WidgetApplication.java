/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package catnap.examples.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gregwhitaker.catnap.springboot.annotation.EnableCatnap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableCatnap
public class WidgetApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WidgetApplication.class, args);
    }

    /*
     * This is how you define a custom object mapper to be used with Catnap. If you
     * do not need a custom object mapper you do not need to define this method as Catnap
     * will create a default object mapper for you.
     */
    @Bean
    public ObjectMapper myCustomObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);

        return mapper;
    }
}
