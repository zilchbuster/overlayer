package com.zilchbuster.overlayer;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zilchbuster.overlayer.Image;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
	Image findOneByToken(String token);
}
