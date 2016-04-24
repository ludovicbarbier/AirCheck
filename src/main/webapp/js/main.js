window.onload = function () {

    var map = L.map("map", {
        center: [0, 0],
        zoom: 2,
        maxZoom: 8,
        crs: L.CRS.EPSG4326,
        maxBounds: [
            [-120, -220],
            [120, 220]
        ]
    });

    var layer = new L.GIBSLayer('MODIS_Terra_CorrectedReflectance_TrueColor', {
        date: new Date('2016/04/24'),
        transparent: true
    }).addTo(map);

    map.addLayer(layer);

    var symptoms = [];
    var symptomsSelected = [];

    $.ajax({
        method: "GET",
        url: "sample/sample.json"
    }).done(function (data) {
        // L.geoJson(data).addTo(map);
        // console.log(data.features[0].properties.SYMPTOMS)

        data.features.forEach(function(feature) {
            var symptom = feature.properties.SYMPTOMS;
            if(symptoms.indexOf(symptom) < 0) {
                symptoms.push(symptom);
            }
        })

        Array.prototype.push.apply(symptomsSelected, symptoms);
        symptoms.forEach(function(symptom) {
            $('#sympton-list').append('<div class="col-md-6 col-sm-6 col-lg-4"><label class="list-group-item"><input type="checkbox" checked name="' + symptom + '" value="' + symptom + '">' + symptom + '</label></div>');
        })

        $('#sympton-list').find('input').change(function(event){
            var checked=event.currentTarget.checked;
            var symptom=event.currentTarget.value;
            if(checked) {
                if(symptomsSelected.indexOf(symptom) < 0) {
                    symptomsSelected.push(symptom);
                }
            } else {
                var index = symptomsSelected.indexOf(symptom);
                if(index > -1) symptomsSelected.splice(index, 1);
            }

        })



        $('#btnUpdateMap').click(function(){

            map.removeLayer(markers)
            var drawingData = $.extend(true, {}, data);

            for(var i = drawingData.features.length-1; i >= 0; i--) {
                var feature = drawingData.features[i];
                var symptom = feature.properties.SYMPTOMS;
                if(symptomsSelected.indexOf(symptom) < 0) {
                    var index = drawingData.features.indexOf(feature);
                    if(index > -1) drawingData.features.splice(index, 1);
                }
            }
            markers=drawMap(drawingData);
        });

        function drawMap(drawData) {
            var markers = L.markerClusterGroup();
            var geoJsonLayer = L.geoJson(drawData, {
                onEachFeature: function (feature, layer) {
                    layer.bindPopup(feature.properties.address);
                }
            });
            markers.addLayer(geoJsonLayer);
            map.addLayer(markers);

            var aqi = L.tileLayer('http://tiles.aqicn.org/tiles/usepa-pm25/{z}/{x}/{y}.png?token=84265a59e803ec10370a06e96898bebef471d30b').addTo(map);
            map.addLayer(aqi);

            return markers;
        }

        var markers = drawMap(data);

    });
};
