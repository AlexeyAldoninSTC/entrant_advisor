let formStatus = true;
let inputForm = document.forms.namedItem("newConditionForm");
let feature = inputForm.elements.namedItem("featureName")
let operator = inputForm.elements.namedItem("operation");
let basicValue = inputForm.elements.namedItem("basicValue");

function checkAddConditionValidation() {

    if (feature.value.length === 0 || operator.value.length === 0 || basicValue.value.length === 0) {
        alert("Empty Fields are not allowed for new Condition");
        formStatus = false;
        return false;
    } else {
        formStatus = true;
        return true;
    }
}

function checkSaveValidation() {
    if (feature.value.length === 0 && operator.value.length === 0 && basicValue.value.length === 0) {
        formStatus = true;
        return true;
    }
    if (feature.value.length !== 0 && operator.value.length !== 0 && basicValue.value.length !== 0) {
        formStatus = true;
        return true;
    }
    alert("Submission allowed only with all fields empty or filled");
    formStatus = false
    return false;
}