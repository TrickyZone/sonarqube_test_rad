DELETE FROM public.technology;

INSERT INTO public.technology(id, name, description, is_new, quadrant, ring, studio, quarter, active)
	VALUES ('bd458343-3108-48ac-b8c0-7334e78e55fc', 'Machine Learning', 'master machine learning', true, '2', '4', 'First', 'assess', true);

INSERT INTO public.technology(id, name, description, is_new, quadrant, ring, studio, quarter, active)
	VALUES ('bd458343-3108-48ac-b8c0-7444e78e55fc', 'AI', 'master AI', false, '4', '1', 'Third', 'assess', false);

INSERT INTO public.technology(id, name, description, is_new, quadrant, ring, studio, quarter, active)
	VALUES ('bd458343-3108-48ac-b8c0-7554e78e55fc', 'Distributed Systems', 'master Distributed Systems', false, '1', '2', 'Second', 'assess', true);

INSERT INTO public.technology(id, name, description, is_new, quadrant, ring, studio, quarter, active)
	VALUES ('bd458343-3108-48ac-b8c0-7664e78e55fc', 'GCP', 'master GCP', true, '3', '3', 'Fourth', 'assess', false);