package org.superbiz.moviefun.moviesapi; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Repository
public class AlbumsClient {

    private final RestOperations restOperations;
    private final String albumUrl;
    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };
    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.albumUrl = albumsUrl;
    }


    public void addAlbum(AlbumInfo albumInfo) {
        restOperations.postForEntity(albumUrl, albumInfo, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(albumUrl + "/" + id , AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumUrl, GET, null, albumListType).getBody();
    }

    public void deleteAlbum(AlbumInfo albumInfo) {
        restOperations.delete(albumUrl+ "/" + albumInfo.getId());
    }

    public void updateAlbum(AlbumInfo albumInfo) {
        restOperations.put(albumUrl+ "/" + albumInfo.getId(), albumInfo, AlbumInfo.class);
    }
}
