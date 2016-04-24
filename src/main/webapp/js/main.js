$(function () {

    // TODO: This can be removed once we have some real data

    /*
     Symptom source
     */
    var features = [];
    var geoJSONSource;
    var clusterSource;
    var styleCache = {};
    var clusters;
    $.ajax({
        method: "GET",
        url: "sample/sample.json"
    }).done(function (data) {
        geoJSONSource = new ol.source.Vector({
            features: (new ol.format.GeoJSON()).readFeatures(data)
        });
        clusterSource = new ol.source.Cluster({
            distance: 40,
            source: geoJSONSource
        });
        clusters = new ol.layer.Vector({
            source: clusterSource,
            style: function (feature) {
                var size = feature.get('features').length;
                var style = styleCache[size];
                if (!style) {
                    style = [new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 10,
                            stroke: new ol.style.Stroke({
                                color: '#fff'
                            }),
                            fill: new ol.style.Fill({
                                color: '#3399CC'
                            })
                        }),
                        text: new ol.style.Text({
                            text: size.toString(),
                            fill: new ol.style.Fill({
                                color: '#fff'
                            })
                        })
                    })];
                    styleCache[size] = style;
                }
                return style;
            }
        });
        clusters.setZIndex(999);
        /*
         WMTS source
         */
        var mapSource = new ol.source.WMTS({
            url: "//map1{a-c}.vis.earthdata.nasa.gov/wmts-geo/wmts.cgi?TIME=2016-04-23",
            layer: "MODIS_Terra_CorrectedReflectance_TrueColor",
            format: "image/jpeg",
            matrixSet: "EPSG4326_250m",
            projection: ol.proj.get("EPSG:4326"),
            tileGrid: new ol.tilegrid.WMTS({
                origin: [-180, 90],
                resolutions: [
                    0.5625,
                    0.28125,
                    0.140625,
                    0.0703125,
                    0.03515625,
                    0.017578125,
                    0.0087890625,
                    0.00439453125,
                    0.002197265625
                ],
                matrixIds: [0, 1, 2, 3, 4, 5, 6, 7, 8],
                tileSize: 512
            })
        });

        var mapLayer = new ol.layer.Tile({
            source: mapSource
        });
        mapLayer.setZIndex(1);

        /*
         KML source for earthdata
         */

        var earthdata = new ol.layer.Vector({
            source: new ol.source.Vector({
                url: 'https://firms.modaps.eosdis.nasa.gov/active_fire/c6/kml/MODIS_C6_Australia_and_New_Zealand_24h.kml',
                format: new ol.format.KML()
            })
        });
        earthdata.setZIndex(100);

        /*
         Get user location
         */

        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("This application is not supported by this browser.");
            }
        }

        function showPosition(position) {
            var map = new ol.Map({
                layers: [mapLayer, clusters],
                target: 'map',
                view: new ol.View({
                    center: [position.coords.longitude, position.coords.latitude],
                    zoom: 3
                }),
                renderer: ["canvas", "dom"]
            });
            map.getView().setCenter(ol.proj.transform([position.coords.longitude, position.coords.latitude], 'EPSG:4326', 'EPSG:3857'));
        }

        getLocation();
    });


});