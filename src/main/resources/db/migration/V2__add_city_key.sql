ALTER TABLE cities ADD COLUMN IF NOT EXISTS city_key VARCHAR(100);

UPDATE cities SET city_key = '6th+of+october' WHERE en_name = '6th of October (city)';
UPDATE cities SET city_key = 'alexandria' WHERE en_name = 'Alexandria';
UPDATE cities SET city_key = 'arish' WHERE en_name = 'Arish';
UPDATE cities SET city_key = 'aswan' WHERE en_name = 'Aswan';
UPDATE cities SET city_key = 'asyut' WHERE en_name = 'Asyut';
UPDATE cities SET city_key = 'banha' WHERE en_name = 'Banha';
UPDATE cities SET city_key = 'beni+suef' WHERE en_name = 'Beni Suef';
UPDATE cities SET city_key = 'bilbeis' WHERE en_name = 'Bilbeis';
UPDATE cities SET city_key = 'cairo' WHERE en_name = 'Cairo';
UPDATE cities SET city_key = 'damanhur' WHERE en_name = 'Damanhur';
UPDATE cities SET city_key = 'damietta' WHERE en_name = 'Damietta';
UPDATE cities SET city_key = 'Mahalla+al+Kubra' WHERE en_name = 'El Mahalla El Kubra';
UPDATE cities SET city_key = 'tor' WHERE en_name = 'El Tor';
UPDATE cities SET city_key = 'faiyum' WHERE en_name = 'Faiyum';
UPDATE cities SET city_key = 'giza' WHERE en_name = 'Giza';
UPDATE cities SET city_key = 'hurghada' WHERE en_name = 'Hurghada';
UPDATE cities SET city_key = 'ismailia' WHERE en_name = 'Ismailia';
UPDATE cities SET city_key = 'kafr+ash+shaykh' WHERE en_name = 'Kafr El Sheikh';
UPDATE cities SET city_key = 'al-khusus' WHERE en_name = 'Khusus';
UPDATE cities SET city_key = 'luxor' WHERE en_name = 'Luxor';
UPDATE cities SET city_key = 'mallawi' WHERE en_name = 'Mallawi';
UPDATE cities SET city_key = 'mansoura' WHERE en_name = 'Mansoura';
UPDATE cities SET city_key = 'marsa+matruh' WHERE en_name = 'Marsa Matrouh';
UPDATE cities SET city_key = 'minya' WHERE en_name = 'Minya';
UPDATE cities SET city_key = 'port+said' WHERE en_name = 'Port Said';
UPDATE cities SET city_key = 'qena' WHERE en_name = 'Qena';
UPDATE cities SET city_key = 'shibin+al+kawm' WHERE en_name = 'Shibin El Kom';
UPDATE cities SET city_key = 'shubra+al+khayma' WHERE en_name = 'Shubra El Kheima';
UPDATE cities SET city_key = 'sohag' WHERE en_name = 'Sohag';
UPDATE cities SET city_key = 'suez' WHERE en_name = 'Suez';
UPDATE cities SET city_key = 'tanta' WHERE en_name = 'Tanta';
UPDATE cities SET city_key = 'zagazig' WHERE en_name = 'Zagazig';

-- Delete cities present in the old data but removed in the new version
DELETE FROM cities WHERE en_name IN ('10th of Ramadan', 'Kharga', 'New Cairo');