package al.golocal.controller;

import al.golocal.dto.AddressDto;
import al.golocal.dto.ApiResponse;
import al.golocal.dto.SiteDto;
import al.golocal.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressDto>> getAddressById(@PathVariable Long id) {
        ApiResponse<AddressDto> apiResponse = new ApiResponse<AddressDto>(0, addressService.getAddressById(id),"");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressDto>> addSite(@RequestBody AddressDto addressDto) {
        ApiResponse<AddressDto> apiResponse = new ApiResponse<AddressDto>(0, addressService.save(addressDto),"");
        return ResponseEntity.ok(apiResponse);
    }
}
