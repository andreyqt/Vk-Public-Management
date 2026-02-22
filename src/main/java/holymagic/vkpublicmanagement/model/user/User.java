package holymagic.vkpublicmanagement.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("is_closed")
    private Boolean isClosed;
    @JsonProperty("can_access_closed")
    private Boolean canAccessClosed;
    private String deactivated;
    private String about;
    private String activities;
    @JsonProperty("bdate")
    private String birthday;
    private Integer blacklisted;
    private City city;
    @JsonProperty("common_count")
    private Integer commonFriends;
    private Country country;
    private String domain;
    @JsonProperty("last_seen")
    private LastSeen lastSeen;
    private Integer hasPhoto;
    private Integer online;
    @JsonProperty("online_mobile")
    private Integer onlineMobile;
    @JsonProperty("online_app")
    private Long onlineApp;
    private Personal personal;
}
