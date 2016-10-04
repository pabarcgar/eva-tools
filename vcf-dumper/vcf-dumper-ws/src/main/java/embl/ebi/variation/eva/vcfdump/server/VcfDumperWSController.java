package embl.ebi.variation.eva.vcfdump.server;

/**
 * Created by pagarcia on 04/10/2016.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping(value = "/webservices/rest/v1/segments")
public @interface VcfDumperWSController {
}
