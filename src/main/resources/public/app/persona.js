
new Vue({
    el: '#personaVue',
    data: {
        personasURL: '/list',
        pagination: {'items-per-page': 5},
        persona: {},
        modalAddPersona: {
            id: 'modalAddPersona',
            header: 'true',
            title: 'Nueva Persona',
            okbtn: 'Registar',
            showaccept: true
        }
    },
    mounted: function () {
        let $vue = this;

    },
    methods: {
        titulo() {
            let $vue = this;

            if ($vue.persona.id != '') {
                $vue.modalAddPersona.title = 'Actualizar Persona';
                $vue.modalAddPersona.okbtn = 'Actualizar';
            } else {
                $vue.modalAddPersona.title = 'Nuevo Persona';
                $vue.modalAddPersona.okbtn = 'Registar';
            }
        },
        nuevo() {
            let $vue = this;
            $vue.persona = {id: '', materno: '', paterno: '', nombres: ''};
            $vue.titulo();
            $vue.$refs.modalAddPersona.open();

        },
        update(item) {
            let $vue = this;
            console.dir(item)
            $vue.persona = item;
            $vue.$refs.modalAddPersona.open();
        },

        save() {
            let $vue = this;

            axios.post('/save', $vue.persona)
                    .then(response => {
                        if (response.data.success) {
                            swal('',
                                    response.data.message,
                                    'success')
                            $vue.$refs.modalAddPersona.close();
                            $vue.$refs.load.loadRemoteData();
                        } else {
                            swal('',
                                    response.data.message,
                                    'error')
                        }
                    })
                    .catch(error => {
                        swal('',
                                'Solicitar ayuda con los de sistemas',
                                'error')
                    });
        },
        deletePersona(item) {
            let $vue = this;
            $vue.persona = item;
            console.log(item)

            swal({
                html: '¿Está seguro de eliminar la persona <strong>' + item.nombres + '</strong>?',
                title: '',
                type: 'warning',
                showCancelButton: true,
                confirmButtonClass: 'btn-danger',
                confirmButtonText: 'Si, eliminar',
                cancelButtonText: "No, cancelar!",
            }).then((result) => {
                if (result.value) {
                    axios.post('/delete', $vue.persona)
                            .then(response => {
                                if (response.data.success) {
                                    swal(
                                            '',
                                            response.data.message,
                                            'success'
                                            )
                                    $vue.$refs.modalAddPersona.close();
                                    $vue.$refs.load.loadRemoteData();
                                } else {
                                    console.log(response.data.success)
                                    swal({
                                        type: 'error',
                                        title: 'Error en eliminar persona'
                                    })
                                }
                            })
                            .catch(error => {
                                swal({
                                    type: 'error',
                                    title: 'Solicitar ayuda con los de sistemas.'
                                })
                            });

                }
            })
        }

    }
});







        