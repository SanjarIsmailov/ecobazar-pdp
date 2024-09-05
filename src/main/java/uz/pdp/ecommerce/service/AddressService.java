package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Address;
import uz.pdp.ecommerce.repo.AddressRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;


    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(UUID id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(UUID id, Address addressDetails) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setFirstName(addressDetails.getFirstName());
                    address.setLastName(addressDetails.getLastName());
                    address.setCompanyName(addressDetails.getCompanyName());
                    address.setStreet(addressDetails.getStreet());
                    address.setCountryOrRegion(addressDetails.getCountryOrRegion());
                    address.setCity(addressDetails.getCity());
                    address.setState(addressDetails.getState());
                    address.setZipCode(addressDetails.getZipCode());
                    address.setEmail(addressDetails.getEmail());
                    address.setPhone(addressDetails.getPhone());
                    return addressRepository.save(address);
                })
                .orElse(null);
    }

    public boolean deleteAddress(UUID id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
