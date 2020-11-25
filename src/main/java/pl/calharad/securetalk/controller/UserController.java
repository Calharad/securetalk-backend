package pl.calharad.securetalk.controller;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import pl.calharad.securetalk.dto.user.LoginResponseTO;
import pl.calharad.securetalk.dto.user.LoginUserTO;
import pl.calharad.securetalk.dto.user.RegisterUserTO;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.service.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("user")
public class UserController {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;
    @Inject
    UserService userService;
    @Inject
    DateTimeFormatter dateTimeFormatter;

    @POST
    @PermitAll
    @Path("register")
    public void register(@Valid RegisterUserTO user) {
        userService.register(user);
    }

    @POST
    @PermitAll
    @Path("login")
    public LoginResponseTO login(@Valid LoginUserTO user) {
        User dbUser = userService.loginUser(user);

        String token = Jwt.issuer(issuer)
                .upn(dbUser.getUsername())
                .groups(Set.of("USER"))
                .claim(Claims.auth_time.name(), dateTimeFormatter.format(LocalDateTime.now()))
                .claim(Claims.nickname.name(), dbUser.getUsername())
                .sign();

        return new LoginResponseTO(token);
    }
}
