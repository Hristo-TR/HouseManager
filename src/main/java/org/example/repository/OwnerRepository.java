package org.example.repository;

import org.example.entity.Owner;

public class OwnerRepository extends BaseRepository<Owner> {
    public OwnerRepository() {
        super(Owner.class);
    }
}
