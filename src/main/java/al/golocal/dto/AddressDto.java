package al.golocal.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class AddressDto {

        private Long addressId;

        private Double latitude;

        private Double longitude;

        private String address;

        private String city;

        private String zipCode;

        private String country;

}
