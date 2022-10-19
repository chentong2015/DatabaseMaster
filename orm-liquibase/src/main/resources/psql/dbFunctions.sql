-- 在执行数据库变更的时候，直接配置SQL脚本
INSERT INTO public.t_person(id, firstname, lastname, twitter)
VALUES (1, 'chen', 'tong', 'twitter01');

INSERT INTO public.t_person(id, firstname, lastname, twitter)
VALUES (2, 'chen', 'tong', 'twitter01');

INSERT INTO public.t_person(id, firstname, lastname, twitter)
VALUES (3, 'chen', 'tong', 'twitter01');

INSERT INTO public.t_person(id, firstname, lastname, twitter)
VALUES (4, 'chen', 'tong', 'twitter01');

INSERT INTO public.t_person(id, firstname, lastname, twitter)
VALUES (5, 'chen', 'tong', 'twitter01');

DELETE
FROM public.t_person
where id = 2;

UPDATE public.t_person
set firstname='new chen'
where id = 3;